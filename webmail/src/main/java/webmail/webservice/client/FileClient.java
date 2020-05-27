package webmail.webservice.client;

import java.text.SimpleDateFormat;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import webmail.wsdl.AssignSinglePermissionRequest;
import webmail.wsdl.AssignSinglePermissionResponse;
import webmail.wsdl.CreateFileRequest;
import webmail.wsdl.CreateFileDAVRequest;
import webmail.wsdl.CreateFileResponse;
import webmail.wsdl.DeleteFileRequest;
import webmail.wsdl.DeleteFileDAVRequest;
import webmail.wsdl.DeleteFileResponse;
import webmail.wsdl.EditFileRequest;
import webmail.wsdl.EditFileDAVRequest;
import webmail.wsdl.EditFileResponse;
import webmail.wsdl.FileListReturn;
import webmail.wsdl.GetFileByPathRequest;
import webmail.wsdl.GetFileByPathDAVRequest;
import webmail.wsdl.GetFileByPathResponse;
import webmail.wsdl.GetFileRequest;
import webmail.wsdl.GetFileDAVRequest;
import webmail.wsdl.GetFileResponse;
import webmail.wsdl.GetFileWithOutStreamRequest;
import webmail.wsdl.GetFileWithOutStreamDAVRequest;
import webmail.wsdl.GetFileWithOutStreamResponse;
import webmail.wsdl.GetRecycledDocsRequest;
import webmail.wsdl.GetRecycledDocsResponse;
import webmail.wsdl.GetSharedFilesByPathRequest;
import webmail.wsdl.GetSharedFilesByPathResponse;
import webmail.wsdl.GetSharedFilesByPathWithContentRequest;
import webmail.wsdl.GetSharedFilesByPathWithContentDAVRequest;
import webmail.wsdl.GetSharedFilesByPathWithContentResponse;
import webmail.wsdl.GetSharedFilesRequest;
import webmail.wsdl.GetSharedFilesResponse;
import webmail.wsdl.GetVCFFileRequest;
import webmail.wsdl.GetVCFFileDAVRequest;
import webmail.wsdl.GetVCFFileResponse;
import webmail.wsdl.HasChildRequest;
import webmail.wsdl.HasChildResponse;
import webmail.wsdl.RecycleFileRequest;
import webmail.wsdl.RecycleFileResponse;
import webmail.wsdl.RemoveAssignedPermissionRequest;
import webmail.wsdl.RemoveAssignedPermissionDAVRequest;
import webmail.wsdl.RemoveAssignedPermissionResponse;
import webmail.wsdl.RenameFileRequest;
import webmail.wsdl.RenameFileResponse;
import webmail.wsdl.RestoreFileRequest;
import webmail.wsdl.RestoreFileResponse;
import webmail.wsdl.RestoreVersionRequest;
import webmail.wsdl.RestoreVersionResponse;
import webmail.wsdl.ShareFileByPathRequest;
import webmail.wsdl.ShareFileByPathResponse;

public class FileClient extends WebServiceGatewaySupport {

	
	
	
	public GetFileWithOutStreamResponse getFileWithOutStreamRequestBC(String path, String userid,String password) {
		GetFileWithOutStreamRequest request = new GetFileWithOutStreamRequest();
		request.setFilePath(path);
		request.setUserid(userid);request.setPassword(password);
		GetFileWithOutStreamResponse response = (GetFileWithOutStreamResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						request,
						new SoapActionCallback(
								"http://localhost:8080/ws/GetFileWithOutStreamRequest"));

		return response;
	}
	
	
	
	

	public GetSharedFilesResponse getSharedFilesRequest(String userid,String password) {
		GetSharedFilesRequest request = new GetSharedFilesRequest();
		request.setUserid(userid);request.setPassword(password);
		GetSharedFilesResponse response = (GetSharedFilesResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						request,
						new SoapActionCallback(
								"http://localhost:8080/ws/GetSharedFilesRequest"));

		return response;
	}


public CreateFileResponse createFileBC(String filename, String parentFolder,
		String userid,String password, String keywords, String notes, byte[] fileContent,long filesize) {

	CreateFileRequest request = new CreateFileRequest();
	request.setFileName(filename);
	request.setParentFile(parentFolder);
	request.setUserid(userid);request.setPassword(password);
	request.setKeywords(keywords);
	request.setFileSize(filesize);
//	String fileContent=IOUtils.toString(is);
	//System.out.println(fileContent);
	request.setNotes(notes);
	request.setFileContent(fileContent);
	CreateFileResponse response = (CreateFileResponse) getWebServiceTemplate()
			.marshalSendAndReceive(
					request,
					new SoapActionCallback(
							"http://localhost:8080/ws/CreateFileRequest"));
	return response;

}
	
	

	public GetFileByPathResponse getFileByPathBC(String filePath, String userid,String password) {
		GetFileByPathRequest request = new GetFileByPathRequest();
		request.setFilePath(filePath);
		request.setUserid(userid);
		request.setPassword(password);

		GetFileByPathResponse response = (GetFileByPathResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						request,
						new SoapActionCallback(
								"http://localhost:8080/ws/GetFileByPathRequest"));

		return response;
	}
	
	public ShareFileByPathResponse shareFileByPath(String filePath,
			String userid,String password, String users, String groups, String userpermissions,
			String grouppermissions) {
		ShareFileByPathRequest request = new ShareFileByPathRequest();
		request.setFilePath(filePath);
		request.setUserid(userid);request.setPassword(password);
		request.setUsers(users);
		request.setGroups(groups);
		request.setUserpermissions(userpermissions);
		request.setGrouppermissions(grouppermissions);

		ShareFileByPathResponse response = (ShareFileByPathResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						request,
						new SoapActionCallback(
								"http://localhost:8080/ws/ShareFileByPathRequest"));

		return response;
	}

	


	public RecycleFileResponse recycleFile(String fileName, String userid,String password) {
		RecycleFileRequest request = new RecycleFileRequest();
		request.setUserid(userid);request.setPassword(password);
		request.setFilePath(fileName);
		RecycleFileResponse deleteResponse = (RecycleFileResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						request,
						new SoapActionCallback(
								"http://localhost:8080/ws/recycleFileRequest"));
		return deleteResponse;
	}

	public RestoreFileResponse restoreFile(String fileName, String userid,String password) {
		RestoreFileRequest request = new RestoreFileRequest();
		request.setUserid(userid);request.setPassword(password);
		request.setFilePath(fileName);
		RestoreFileResponse deleteResponse = (RestoreFileResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						request,
						new SoapActionCallback(
								"http://localhost:8080/ws/restoreFileRequest"));
		return deleteResponse;
	}


public GetRecycledDocsResponse getRecycledDoc(String userid,String password,
			String folderName) {
		GetRecycledDocsRequest request = new GetRecycledDocsRequest();
		request.setUserid(userid);request.setPassword(password);
		request.setPath(folderName);
		GetRecycledDocsResponse deleteResponse = (GetRecycledDocsResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						request,
						new SoapActionCallback(
								"http://localhost:8080/ws/getRecycledDocsRequest"));
		return deleteResponse;
	}

	public RenameFileResponse renameFile(String oldFileName,
			String newFileName, String userid,String password) {
		RenameFileRequest request = new RenameFileRequest();
		request.setOldName(oldFileName);
		request.setNewName(newFileName);
		request.setUserid(userid);request.setPassword(password);
		RenameFileResponse renameResponse = (RenameFileResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						request,
						new SoapActionCallback(
								"http://localhost:8080/ws/renameFileRequest"));
		return renameResponse;
	}

	

}
