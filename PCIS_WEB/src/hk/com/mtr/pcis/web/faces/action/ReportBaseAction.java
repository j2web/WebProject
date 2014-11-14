package hk.com.mtr.pcis.web.faces.action;

import hk.com.mtr.pcis.criteria.AppBaseCriteriaVO;
import hk.com.mtr.pcis.report.ReportFactory;
import hk.com.mtr.pcis.web.faces.util.Constant;
import hk.com.mtr.pcis.web.faces.util.FacesUtil;

import org.jboss.seam.international.StatusMessage;

public abstract class ReportBaseAction extends AppBaseAction {

	private static final long serialVersionUID = -2137156171983986770L;

	public void doReport() {
		try {
			boolean canGenerate = false;
			try {
				canGenerate = ReportFactory.checkMaxCounter();
				if (canGenerate) {
					try {
						onReport();
						FacesUtil.getSession().setAttribute(Constant.REPORT_FLAG, Boolean.TRUE);
					} catch (Exception e) {
						this.handleException(e);
					}
				} else {
					facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "com.mtrc.pcis.generateReport.maxConcurrent");
					return;
				}
			} catch (Exception e) {
				log.trace("can not generate report", e);
			} finally {
				ReportFactory.deductCounter();
			}
		} catch (Exception e) {
			this.handleException(e);
		}
	}

	public String doReset() {

		return onReset();

	}

	protected void saveCriteriaVO(AppBaseCriteriaVO appBaseCriteriaVO) {
		FacesUtil.saveCriteriaVO(appBaseCriteriaVO);
	}

	protected void resetCriteriaVO(AppBaseCriteriaVO appBaseCriteriaVO) {
		FacesUtil.resetCriteriaVO(appBaseCriteriaVO);
	}

	protected abstract void onReport() throws Exception;

	protected abstract String onReset();
}
