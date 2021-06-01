package org.etlt.load;

import org.etlt.EtltRuntimeException;
import org.etlt.expression.ExpressionCompiler;
import org.etlt.extract.Extractor;
import org.etlt.job.JobContext;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
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
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(setting.getTarget()),
                            Charset.forName(setting.getEncoding())));
            List<ColumnSetting> columns = setting.getColumns();
            if (setting.isUsingBanner())
                writeBanner(bufferedWriter);
            String ds = setting.getExtractors().get(0);
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
            throw new EtltRuntimeException("load execution error: " + getName(), e);
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
