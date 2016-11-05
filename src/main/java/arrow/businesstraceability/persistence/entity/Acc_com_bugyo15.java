//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import arrow.businesstraceability.constant.AccConstants;
import arrow.businesstraceability.persistence.dto.Acc_com_bugyo15_DTO;
import arrow.businesstraceability.persistence.mapped.Acc_com_bugyo15_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Acc_com_bugyo15 extends Acc_com_bugyo15_MAPPED {

    public Acc_com_bugyo15() {
    }

    public Acc_com_bugyo15(final java.lang.String code) {
        super(code);
    }

    public static Acc_com_bugyo15 find(final java.lang.String code) {
        return EmLocator.getEm().find(Acc_com_bugyo15.class, new Acc_com_bugyo15.Pk(code));
    }

    public Acc_com_bugyo15_DTO getDto() {
        return DtoUtils.createDto(this, Acc_com_bugyo15_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Transient
    private Integer min;

    public Integer getMin() {
        if (StringUtils.isEmpty(this.getTitle()) || AccConstants.JP_NO_DATA_CODE.equalsIgnoreCase(this.getCode())) {
            this.min = null;
        } else {
            this.min = this.extractMinMaxValue(this.getTitle(), true);
        }
        return this.min;
    }

    @Transient
    private Integer max;

    public Integer getMax() {
        if (StringUtils.isEmpty(this.getTitle()) || AccConstants.JP_NO_DATA_CODE.equalsIgnoreCase(this.getCode())) {
            this.max = null;
        } else {
            this.max = this.extractMinMaxValue(this.getTitle(), false);
        }
        return this.max;
    }

    private Integer extractMinMaxValue(final String title, final boolean isGetMin) {
        Pattern ptn = Pattern.compile("[A-D]\\d?\\s\\((\\d{1,2})\\D?(\\d{2,3})\\)");
        Matcher matcher = ptn.matcher(title);
        boolean found = matcher.find();
        if (!found) {
            return null;
        }
        if (isGetMin) {
            return Integer.parseInt(matcher.group(1));
        } else {
            return Integer.parseInt(matcher.group(2));
        }
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}