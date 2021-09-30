package org.etlt.validate;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.etlt.EtltRuntimeException;
import org.etlt.job.JobContext;

import java.io.*;
import java.security.MessageDigest;

public class FileValidator extends Validator {

    private final String name;

    public FileValidator(FileValidatorSetting setting) {
        super(setting);
        this.name = getSetting().getName();
    }

    @Override
    public void validate(JobContext context) {
        BufferedWriter result = null;
        try {
            result = getResult(context);
            result.write(generateReport(context));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                try {
                    result.flush();
                    result.close();
                } catch (IOException e1) {

                }
            }
        }
    }

    protected String generateReport(JobContext context) {
        FileValidatorSetting setting = getSetting();
        File sourceFile = new File(context.getContextRoot(), setting.getSource());
        File targetFile = new File(context.getContextRoot(), setting.getTarget());
        String sourceMD5 = getMD5(sourceFile);
        String targetMD5 = getMD5(targetFile);
        String conclusion = sourceMD5.equals(targetMD5) ? "SAME" : "DIFFERENT";
        final String LF = "\n";
        StringBuilder template0 = new StringBuilder("---validation result for ${name}---")
                .append(LF)
                .append("---MD5 for source file ${sourceFile}: ${MD5_1}")
                .append(LF)
                .append("---MD5 for target file ${targetFile}: ${MD5_2}")
                .append(LF)
                .append("---conclusion: ${conclusion}")
                .append(LF);
        String report = template0.toString();
        report = StringUtils.replace(report, "${name}", setting.getName());
        report = StringUtils.replace(report, "${sourceFile}", setting.getSource());
        report = StringUtils.replace(report, "${MD5_1}", sourceMD5);
        report = StringUtils.replace(report, "${targetFile}", setting.getTarget());
        report = StringUtils.replace(report, "${MD5_2}", targetMD5);
        report = StringUtils.replace(report, "${conclusion}", conclusion);
        return report;
    }

    /**
     * calculate md5 for a file
     *
     * @param file
     * @return
     */
    public static String getMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (Exception e) {
            throw new EtltRuntimeException(e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                // -- do nothing
            }
        }
    }

    protected BufferedWriter getResult(JobContext context) throws FileNotFoundException {
        String result = StringUtils.isBlank(getSetting().getResult()) ? getName() + ".result" : getSetting().getResult();
        File file = new File(context.getContextRoot(), result);
        for (int i = 1; file.exists(); i++) {
            file = new File(context.getContextRoot(), getSetting().getResult() + "(" + i + ")");
        }
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
    }
}
