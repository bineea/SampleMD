package my.sample.common.pub;

public class MyManagerException extends Exception {

	private static final long serialVersionUID = -8394443708643384587L;
	
	private String errorCode;

	public MyManagerException(){
		
		super();
	}
	
	public MyManagerException(String msg){
		
		super(msg);
	}
	
	public MyManagerException(String errorCode, String msg) {
		
		super(msg);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
}
