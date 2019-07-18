package my.sample.common.tools;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import my.sample.common.pub.MyManagerException;

public class ImageUploadTools {
	
	public static File uploadImage(MultipartHttpServletRequest  multipartRequest, String directory) throws IOException, MyManagerException {
		Iterator<String> iter = multipartRequest.getFileNames();
		while(iter.hasNext()) {
			MultipartFile file = multipartRequest.getFile(iter.next());
			if (file != null) {
				String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
				if(!checkImageType(suffix, file.getBytes()))
					throw new MyManagerException("无效的文件格式（允许上传jpeg|jpg|png|gif格式文件）");
				if(file.getSize() > 1024*1024*2)
					throw new MyManagerException("文件大小超过2M");
				File fileDir = new File(directory);
				if(!fileDir.exists() || !fileDir.isDirectory())
					fileDir.mkdirs();
				File uploadFile = new File(fileDir.getAbsolutePath() + File.separator + UUID.randomUUID().toString() + suffix);
				file.transferTo(uploadFile);
				return uploadFile;
			}
		}
		return null;
	}
	
	public static boolean checkImageType(String suffix, byte[] fileBytes) {
		String fileHexStr = MyTools.bytesToHexString(fileBytes, 5).toUpperCase();
		if((suffix.equals(".jpeg") || suffix.equals(".jpg")) && fileHexStr.startsWith("FFD8FF")) {
			return true;
		} else if(suffix.equals(".png") && fileHexStr.startsWith("89504E47")) {
			return true;
		} else if(suffix.equals(".gif") && fileHexStr.startsWith("47494638")) {
			return true;
		} 
		return false;
	}
	
}
