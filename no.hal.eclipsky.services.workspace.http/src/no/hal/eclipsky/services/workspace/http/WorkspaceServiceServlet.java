package no.hal.eclipsky.services.workspace.http;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import no.hal.eclipsky.services.workspace.WorkspaceService;

@SuppressWarnings("serial")
public abstract class WorkspaceServiceServlet extends HttpServlet {

	private WorkspaceService workspaceService;

	public WorkspaceService getWorkspaceService() {
		return workspaceService;
	}

	public synchronized void setWorkspaceService(WorkspaceService workspaceService) {
		this.workspaceService = workspaceService;
	}

	@Override
	public void destroy() {
		super.destroy();
		workspaceService = null;
	}

	protected String getResponseFormat(HttpServletRequest request, String def) {
		String responseFormat = request.getParameter("format");
		if (responseFormat == null) {
			responseFormat = def;
		}
		return responseFormat;
	}

	protected String getResponseFormat(HttpServletRequest request) {
		return getResponseFormat(request, "html");
	}
	
	protected ResponseFormatter getResponseFormatter(String responseFormat, PrintWriter writer) {
		switch (responseFormat) {
		case "xml": return new XmlResponseFormatter(writer);
		case "json": return new JsonResponseFormatter(writer);
		}
		return null;
	}
}
