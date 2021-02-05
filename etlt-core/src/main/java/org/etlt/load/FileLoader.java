package org.etlt.load;

import org.etlt.extract.Extractor;
import org.etlt.job.JobContext;
import org.etlt.expression.ExpressionCompiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileLoader extends Loader {

    public static final String NEXT_LINE = "\n";

    private BufferedWriter bufferedWriter = null;

    public FileLoader(FileLoaderSetting setting) {
        super(setting);
        setName(setting.getName());
    }

    @Override
    public void preLoad(JobContext context) {

    }

    /**
     * @param context
     */
    @Override
    public void load(JobContext context) {
        try {
            resolveColumns(context);
            FileLoaderSetting setting = getSetting();
            bufferedWriter = new BufferedWriter(new FileWriter(new File(setting.getTarget())));
            List<ColumnSetting> columns = setting.getColumns();
            if (setting.isUsingBanner())
                writeBanner(bufferedWriter);
            String ds = setting.getExtractor();
            Extractor extractor = context.getExtractor(ds);
            ExpressionCompiler expressionCompiler = new ExpressionCompiler();
            for (extractor.extract(context); context.isExist(ds); extractor.extract(context)) {
                StringBuilder sb = new StringBuilder();
                for (ColumnSetting column : columns) {
                    Object result = expressionCompiler.evaluate(column.getExpression(), context);
                    sb.append(String.valueOf(result)).append(setting.getDelim());
                }
                //--remove the last character
                if (sb.length() > 0)
                    sb.deleteCharAt(sb.length() - 1);
                sb.append(NEXT_LINE);
                bufferedWriter.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFinish() {
        try {
            if (this.bufferedWriter != null) {
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeBanner(BufferedWriter writer) throws IOException {
        FileLoaderSetting setting = getSetting();
        StringBuilder sb = new StringBuilder();
        for (ColumnSetting column : setting.getColumns()) {
            sb.append(column.getName()).append(setting.getDelim());
        }
        //--remove the last character
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        sb.append(NEXT_LINE);
        bufferedWriter.write(sb.toString());
    }
}
