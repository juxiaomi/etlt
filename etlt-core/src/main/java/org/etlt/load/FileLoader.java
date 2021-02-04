package org.etlt.load;

import org.etlt.EtltException;
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

    private final FileLoaderSetting setting;

    private BufferedWriter bufferedWriter = null;

    public FileLoader(FileLoaderSetting setting) {
        this.setting = setting;
        setName(setting.getName());
    }

    /**
     * @param context
     */
    @Override
    public void load(JobContext context) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(this.setting.getTarget())));
            List<ColumnSetting> columns = this.setting.getColumns();
            if (this.setting.isUsingBanner())
                writeBanner(bufferedWriter);
            String ds = this.setting.getDs();
            Extractor extractor = context.getExtractor(ds);
            if(extractor == null){
                throw new EtltException("extractor not found: " + ds);
            }
            ExpressionCompiler expressionCompiler = new ExpressionCompiler();
            for (extractor.extract(context); context.isExist(ds); extractor.extract(context)) {
                StringBuilder sb = new StringBuilder();
                for (ColumnSetting column : columns) {
                    Object result = expressionCompiler.evaluate(column.getExpression(), context);
                    sb.append(String.valueOf(result)).append(this.setting.getDelim());
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
        StringBuilder sb = new StringBuilder();
        for (ColumnSetting column : this.setting.getColumns()) {
            sb.append(column.getName()).append(this.setting.getDelim());
        }
        //--remove the last character
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        sb.append(NEXT_LINE);
        bufferedWriter.write(sb.toString());
    }
}
