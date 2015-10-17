/*
 * This file is part of the Emulation-as-a-Service framework.
 *
 * The Emulation-as-a-Service framework is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * The Emulation-as-a-Service framework is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Emulation-as-a-Software framework.
 * If not, see <http://www.gnu.org/licenses/>.
 */

//package de.bwl.bwfla.workflows.shibboleth.util;
//
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import edu.kit.scc.webreg.entity.AdminUserEntity;
//import edu.kit.scc.webreg.entity.RoleEntity;
//import edu.kit.scc.webreg.service.AdminUserService;
//import edu.kit.scc.webreg.service.UserService;
//
//@Named
//@WebFilter(urlPatterns = { "/faces/pages/workflow-shibboleth/*" })
//public class SecurityFilter implements Filter {
//
//	Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
//
//	@Inject
//	private SessionManager session;
//
//	@Inject
//	private AccessChecker accessChecker;
//
//	@Inject
//	private UserService userService;
//
//	@Inject
//	private AdminUserService adminUserService;
//
//	@Override
//	public void destroy() {
//	}
//
//	@Override
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
//			ServletException {
//
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
//		HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//		if (request.getCharacterEncoding() == null) {
//			request.setCharacterEncoding("UTF-8");
//		}
//
//		String context = request.getServletContext().getContextPath();
//		String path = request.getRequestURI().substring(context.length());
//
//		if (path.startsWith("/resources/") || path.startsWith("/javax.faces.resource/") || path.startsWith("/welcome/")
//				|| path.startsWith("/Shibboleth.sso/") || path.equals("/favicon.ico")) {
//			chain.doFilter(servletRequest, servletResponse);
//		} else if (path.startsWith("/register/") && session != null && session.isUserInRole("ROLE_New")) {
//			chain.doFilter(servletRequest, servletResponse);
//		} else if ((path.startsWith("/admin") || path.startsWith("/rest")) && (session == null || (!session.isLoggedIn()))) {
//			processAdminLogin(path, request, response, chain);
//		} else if (session != null && session.isLoggedIn()) {
//
//			Set<String> roles = convertRoles(userService.findRolesForUserById(session.getUserId()));
//			session.setRoles(roles);
//
//			if (accessChecker.check(path, roles))
//				chain.doFilter(servletRequest, servletResponse);
//			else
//				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not allowed");
//		} else {
//			logger.debug("User from {} not logged in. Redirecting to welcome page", request.getRemoteAddr());
//			// session.setOrinignalRequestPath(getFullURL(request));
//			// request.getServletContext().getRequestDispatcher("/").forward(servletRequest,
//			// servletResponse);
//			chain.doFilter(servletRequest, servletResponse);
//		}
//
//	}
//
//	@Override
//	public void init(FilterConfig config) throws ServletException {
//	}
//
//	private Set<String> convertRoles(List<RoleEntity> roleList) {
//		Set<String> roles = new HashSet<String>();
//		for (RoleEntity role : roleList)
//			roles.add("ROLE_" + role.getName());
//
//		return roles;
//	}
//
//	private void processAdminLogin(String path, HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//
//		String auth = request.getHeader("Authorization");
//		if (auth != null) {
//			int index = auth.indexOf(' ');
//			if (index > 0) {
//				String[] credentials = StringUtils.split(new String(Base64.decodeBase64(auth.substring(index).getBytes())), ':');
//
//				if (credentials.length == 2) {
//					AdminUserEntity adminUser = adminUserService.findByUsernameAndPassword(credentials[0], credentials[1]);
//
//					if (adminUser != null) {
//						List<RoleEntity> roleList = adminUserService.findRolesForUserById(adminUser.getId());
//						Set<String> roles = convertRoles(roleList);
//
//						session.setRoles(roles);
//
//						if (accessChecker.check(path, roles))
//							chain.doFilter(request, response);
//						else
//							response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not allowed");
//
//						return;
//					}
//				}
//			}
//		}
//
//		response.setHeader("WWW-Authenticate", "Basic realm=\"Admin Realm\"");
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//	}
//
//	@SuppressWarnings("unused")
//	private String getFullURL(HttpServletRequest request) {
//		StringBuilder sb = new StringBuilder(request.getRequestURI());
//		String query = request.getQueryString();
//
//		if (query != null) {
//			sb.append("?");
//			sb.append(query);
//		}
//
//		return sb.toString();
//	}
//}
