package org.etlt.extract;

import org.etlt.job.JobContext;
import org.etlt.load.ColumnSetting;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileExtractor extends Extractor {
    final FileExtractSetting setting;

    private BufferedReader reader = null;

    private int skip = 0;

    public FileExtractor(FileExtractSetting setting) {
        this.setting = setting;
        this.setName(setting.getName());
    }

    @Override
    public void extract(JobContext context) {
        try {
            if (reader == null) {
                //use file existed in job config directory
                reader = new BufferedReader(new FileReader(new File(context.getConfigDirectory(), setting.getDataSource())));
            }
            String text = reader.readLine();

            if (text != null) {
                if (this.skip < this.setting.getSkip()) {
                    this.skip++;
                    extract(context);
                } else
                    context.setEntity(this.setting.getName(), parse(text));
            } else {
                context.removeEntity(this.setting.getName());
                doFinish();
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    private Map<String, Object> parse(String text) {
        Map<String, Object> result = new HashMap<>();
        String columns[] = text.split(this.setting.getDelim());
        List<String> columnNames = setting.getColumns();
        for (int i = 0; i < columns.length; i++) {
            result.put(columnNames.get(i), columns[i]);
        }
        return result;
    }
}
