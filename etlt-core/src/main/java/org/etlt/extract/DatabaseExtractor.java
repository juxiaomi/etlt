package org.etlt.extract;

import org.etlt.job.JobContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//todo
public class DatabaseExtractor extends Extractor {
    final DatabaseExtractSetting setting;

    private BufferedReader reader = null;

    private int skip = 0;

    public DatabaseExtractor(DatabaseExtractSetting setting) {
        this.setting = setting;
        this.setName(setting.getName());
    }

    @Override
    public void extract(JobContext context) {
        try {
            if (reader == null) {
                //use file existed in job config directory
                reader = new BufferedReader(new FileReader(new File(context.getConfigDirectory(), setting.getDataSource().getUrl())));
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
    public void doFinish() {
        if (this.reader != null) {
            try {
                this.reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, String> parse(String text) {
        Map<String, String> result = new HashMap<>();
        return result;
    }
}
