package org.ffsc.ssp.web.beans.layout;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.faces.application.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.ffsc.ssp.web.beans.layout.model.MenuItem;
import org.ffsc.ssp.web.beans.layout.model.PanelMenu;
import org.ffsc.ssp.web.beans.layout.model.SubMenu;
import org.ffsc.ssp.web.security.UserInfo;
import org.ffsc.ssp.web.util.FacesContextHelper;
import org.ffsc.ssp.web.util.XMLHelper;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@ManagedBean(eager=true)
@SessionScoped
public class MenuBean {

	private static final String MENU_METADATA_PATH = "menu/#context#/menu-items.xml"; 
	
	private MenuModel model;
	
	@ManagedProperty(value="#{userInfo}")
	private UserInfo userInfo;
	
	@PostConstruct
	public void initialize() {
		
		PanelMenu panelMenu = null;
		InputStream inputStream = null;
		
		try {
			
			String menuMetadataPath = MENU_METADATA_PATH.replace("#context#", userInfo.getContext());
			
			Resource menuMetadataFile = FacesContextHelper.getResourceHandler().createResource(menuMetadataPath);
			
			inputStream = menuMetadataFile.getInputStream();
			
			panelMenu = (PanelMenu) XMLHelper.getXStream().fromXML(inputStream);
			
			buildModel(panelMenu);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void buildModel(PanelMenu panelMenu){
		
		model = new DefaultMenuModel();

		for (SubMenu sbm : panelMenu.getSubMenus()) {

			DefaultSubMenu submenu = new DefaultSubMenu();

			submenu.setLabel(FacesContextHelper.evaluateEL(sbm.getLabel()));
			submenu.setIcon(sbm.getIcon());

			for (MenuItem mni : sbm.getMenuItems()) {

				DefaultMenuItem menuitem = new DefaultMenuItem();

				menuitem.setValue(FacesContextHelper.evaluateEL(mni.getValue()));
				menuitem.setUrl(mni.getUrl());
				menuitem.setIcon(mni.getIcon());

				submenu.addElement(menuitem);
			}

			model.addElement(submenu);
		}
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}