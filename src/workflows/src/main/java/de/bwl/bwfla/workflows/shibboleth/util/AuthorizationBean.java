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
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import javax.annotation.PostConstruct;
//import javax.enterprise.context.RequestScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//import edu.kit.scc.webreg.entity.AdminRoleEntity;
//import edu.kit.scc.webreg.entity.ApproverRoleEntity;
//import edu.kit.scc.webreg.entity.GroupEntity;
//import edu.kit.scc.webreg.entity.RegistryEntity;
//import edu.kit.scc.webreg.entity.RegistryStatus;
//import edu.kit.scc.webreg.entity.RoleEntity;
//import edu.kit.scc.webreg.entity.ServiceEntity;
//import edu.kit.scc.webreg.entity.UserEntity;
//import edu.kit.scc.webreg.service.GroupService;
//import edu.kit.scc.webreg.service.RegistryService;
//import edu.kit.scc.webreg.service.RoleService;
//import edu.kit.scc.webreg.service.ServiceService;
//import edu.kit.scc.webreg.service.UserService;
//import edu.kit.scc.webreg.service.reg.RegisterUserService;
//import edu.kit.scc.webreg.service.reg.SetPasswordCapable;
//
//@Named("authorizationBean")
//@RequestScoped
//public class AuthorizationBean implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private List<ServiceEntity> unregisteredServiceList;
//	private List<RegistryEntity> userRegistryList;
//	private List<ServiceEntity> serviceApproverList;
//	private List<ServiceEntity> serviceAdminList;
//		
//    @Inject
//    private RegistryService registryService;
//
//    @Inject
//    private RoleService roleService;
//
//    @Inject
//    private ServiceService serviceService;
//    
//    @Inject 
//    private SessionManager sessionManager;
//    
//    @Inject
//    private UserService userService;
//    
//    @Inject
//    private GroupService groupService;
//    
//    @Inject
//    private RegisterUserService registerUserService;
//    
//    @PostConstruct
//    private void init() {
//    	if (sessionManager.getUserId() == null)
//    		return;
//    	
//    	UserEntity user = userService.findById(sessionManager.getUserId());
//    	List<GroupEntity> groupList = groupService.findByUser(user);
//    	String groupString = groupsToString(groupList);
//    	
//    	userRegistryList = registryService.findByUserAndStatus(user, RegistryStatus.ACTIVE);
//    	serviceApproverList = new ArrayList<ServiceEntity>();
//    	serviceAdminList = new ArrayList<ServiceEntity>();
//    	
//    	unregisteredServiceList = serviceService.findAllPublishedWithServiceProps();
//    	
//    	for (RegistryEntity registry : userRegistryList) {
//    		unregisteredServiceList.remove(registry.getService());
//    	}
//    	
//    	List<ServiceEntity> serviceToRemove = new ArrayList<ServiceEntity>();
//    	for (ServiceEntity s : unregisteredServiceList) {
//    		Map<String, String> serviceProps = s.getServiceProps();
//
//    		if (serviceProps.containsKey("idp_filter")) {
//    			String idpFilter = serviceProps.get("idp_filter");
//    			if (idpFilter != null &&
//    					(! idpFilter.contains(user.getPersistentIdpId())))
//    				serviceToRemove.add(s);
//    		}
//
//    		if (s.getServiceProps().containsKey("group_filter")) {
//    			String groupFilter = serviceProps.get("group_filter");
//    			if (groupFilter != null &&
//    					(! groupString.matches(groupFilter)))
//    				serviceToRemove.add(s);
//    		}
//
//    		if (s.getServiceProps().containsKey("entitlement_filter")) {
//    			String entitlementFilter = serviceProps.get("entitlement_filter");
//    			String entitlement = user.getAttributeStore().get("urn:oid:1.3.6.1.4.1.5923.1.1.1.7");
//    			if (entitlementFilter != null && entitlement != null &&
//    					(! entitlement.matches(entitlementFilter)))
//    				serviceToRemove.add(s);
//    		}
//    	}
//    	unregisteredServiceList.removeAll(serviceToRemove);
//    	
//    	List<RoleEntity> roleList = roleService.findByUser(user);
//    	
//    	for (RoleEntity role : roleList) {
//    		if (role instanceof AdminRoleEntity) {
//    			serviceAdminList.addAll(serviceService.findByAdminRole(role));
//    		}
//    		else if (role instanceof ApproverRoleEntity) {
//        		serviceApproverList.addAll(serviceService.findByApproverRole(role));
//    		}
//    	}
//	}
//
//    public boolean isUserInService(ServiceEntity service) {
//    	if (service == null)
//    		return false;
//    	
//    	for (RegistryEntity registry : userRegistryList) {
//    		if (registry.getService().getId().equals(service.getId()))
//    			return true;
//    	}
//    	return false;
//    }
//    
//    public boolean isUserServiceAdmin(Long id) {
//    	if (id == null)
//    		return false;
//    	
//    	for (ServiceEntity service : getServiceAdminList()) {
//    		if (id.equals(service.getId()))
//    			return true;
//    	}
//    	return false;
//    }
//
//    public List<RegistryEntity> getUserRegistryList() {
//    	if (userRegistryList == null) init();
//   		return userRegistryList;
//    }
//
//	public List<ServiceEntity> getServiceApproverList() {
//    	if (serviceApproverList == null) init();
//		return serviceApproverList;
//	}
//
//	public List<ServiceEntity> getServiceAdminList() {
//    	if (serviceAdminList == null) init();
//		return serviceAdminList;
//	}
//
//	public boolean isPasswordCapable(ServiceEntity serviceEntity) {
//		try {
//			return (registerUserService.getWorkflowInstance(serviceEntity.getRegisterBean()) 
//					instanceof SetPasswordCapable);
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	public List<ServiceEntity> getUnregisteredServiceList() {
//		return unregisteredServiceList;
//	}
//
//	private String groupsToString(List<GroupEntity> groupList) {
//		StringBuilder sb = new StringBuilder();
//		for (GroupEntity group : groupList) {
//			if (group.getPrefix() != null) {
//				sb.append(group.getPrefix());
//			}
//			sb.append("_");
//			sb.append(group.getName());
//			sb.append(";");
//		}
//		if (sb.length() > 0)
//			sb.setLength(sb.length() - 1);
//		
//		return sb.toString();
//	}
//}
