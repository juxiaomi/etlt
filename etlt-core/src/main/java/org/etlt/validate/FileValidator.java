package org.etlt.validate;

import org.apache.commons.codec.binary.Hex;
import org.etlt.EtltRuntimeException;
import org.etlt.job.JobContext;

import java.io.*;
import java.security.MessageDigest;

public class FileValidator extends Validator{

    private final String name;

    public FileValidator(FileValidatorSetting setting) {
        super(setting);
        this.name = getSetting().getName();
    }

    @Override
    public void validate(JobContext context) {
        FileValidatorSetting setting = getSetting();
        File sourceFile = new File(context.getContextRoot(), setting.getSource());
        File targetFile = new File(context.getContextRoot(), setting.getTarget());
        String sourceMD5 = getMD5(sourceFile);
        String targetMD5 = getMD5(targetFile);
        BufferedWriter result = null;
        try{
            result = getResult(context);
            result.write(sourceMD5);
            result.write("\n");
            result.write(targetMD5);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(result != null) {
                try {
                    result.flush();
                    result.close();
                }catch (IOException e1){

                }
            }
        }
    }

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
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                // -- do nothing
            }
        }
    }

    protected BufferedWriter getResult(JobContext context) throws FileNotFoundException {
        File file = new File(context.getContextRoot(), getSetting().getResult());
        for(int i = 1; file.exists() ;i++){
            file = new File(context.getContextRoot(), getSetting().getResult() + "(" + i + ")");
        }
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
    }
}
