package edu.wm.cs420;

public class JSONResponse {

	private int status;
	private Object object;
	private Class<?> objClass;
	
	public JSONResponse() {
		status = 0;
		object = null;
		objClass = null;
	}
	
	public JSONResponse(int status, Object object) {
		this.status = status;
		this.object = object;
		if (object == null) {
			this.objClass = null;
		} else {
			this.objClass = object.getClass();
		}
	}

	public Class<?> getObjClass() {
		return objClass;
	}

	public void setObjClass(Class<?> objClass) {
		this.objClass = objClass;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
}
