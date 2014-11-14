package hk.com.mtr.pcis.web.faces.util;

import java.io.Serializable;
import org.jboss.seam.ScopeType;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.richfaces.component.UITree;
import org.richfaces.component.state.TreeStateAdvisor;
import org.richfaces.model.TreeRowKey;

@Name("treeNodeAdvisor")
@Scope(ScopeType.APPLICATION)
@BypassInterceptors
public class TreeNodeAdvisor implements TreeStateAdvisor, Serializable {

	private static final long serialVersionUID = -6105773933859552502L;

	public Boolean adviseNodeOpened(UITree tree) {
		if (!FacesUtil.isPostback()) {
			Object key = tree.getRowKey();
			TreeRowKey<?> treeRowKey = (TreeRowKey<?>) key;
			if (treeRowKey == null || treeRowKey.depth() < 2) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		}
		return null;

	}

	public Boolean adviseNodeSelected(UITree tree) {
		return null;
	}

}
