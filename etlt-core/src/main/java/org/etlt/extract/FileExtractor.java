package org.etlt.extract;

import org.etlt.EtltException;
import org.etlt.EtltRuntimeException;
import org.etlt.job.JobContext;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileExtractor extends Extractor {
    final FileExtractSetting setting;

    private BufferedReader reader = null;

    public FileExtractor(FileExtractSetting setting) {
        this.setting = setting;
        this.setName(setting.getName());
    }

    @Override
    public void extract(JobContext context) {
        try {
            if (reader == null) {
                //use file existed in job config directory
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                        new File(context.getContextRoot(), setting.getDataSource())), Charset.forName(this.setting.getEncoding()))
                    );
            }
            String text = reader.readLine();

            if (text != null) {
                if (this.skip < this.setting.getSkip()) {
                    this.skip++;
                    this.index++;
                    extract(context);
                } else
                    context.setEntity(this.setting.getName(), parse(index++, text));
            } else {
                context.removeEntity(this.setting.getName());
                doFinish();
            }
        } catch (IOException e) {
            throw new EtltRuntimeException("extractor execution error: " + getName(), e);
        }
    }

    @Override
    public List<String> getColumns() {
        return this.setting.getColumns();
    }

    @Override
    public void doFinish() {
        close(reader);
    }

    private Entity parse(int index, String text) {
        Map<String, Object> result = new HashMap<>();
        String concreteColumns[] = text.split(this.setting.getDelim());
        List<String> settingColumns = setting.getColumns();
        for (int i = 0; i < Math.min(concreteColumns.length, settingColumns.size()); i++) {
            result.put(settingColumns.get(i), concreteColumns[i]);
        }
        return new Entity(index, result);
    }
}
