package org.etlt.extract;

import org.etlt.EtltException;
import org.etlt.job.JobContext;
import org.etlt.load.ColumnSetting;

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
                        new File(context.getConfigDirectory(), setting.getDataSource())), Charset.forName(this.setting.getEncoding()))
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
            throw new EtltException("extractor execution error: " + getName(), e);
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
        String columns[] = text.split(this.setting.getDelim());
        List<String> columnNames = setting.getColumns();
        for (int i = 0; i < columns.length; i++) {
            result.put(columnNames.get(i), columns[i]);
        }
        return new Entity(index, result);
    }
}
