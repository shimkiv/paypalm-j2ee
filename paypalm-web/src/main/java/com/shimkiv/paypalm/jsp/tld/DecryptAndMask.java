/*
 * All materials herein: Copyright (c) 2000-2017 Serhii Shymkiv. All Rights Reserved.
 *
 * These materials are owned by Serhii Shymkiv and are protected by copyright laws
 * and international copyright treaties, as well as other intellectual property laws
 * and treaties.
 *
 * All right, title and interest in the copyright, confidential information,
 * patents, design rights and all other intellectual property rights of
 * whatsoever nature in and to these materials are and shall remain the sole
 * and exclusive property of Serhii Shymkiv.
 */

package com.shimkiv.paypalm.jsp.tld;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

import static com.shimkiv.paypalm.crypto.Crypter.decrypt;
import static com.shimkiv.paypalm.utils.CommonUtils.maskString;

/**
 * Custom JSP tag (decrypt and mask)
 *
 * @author Serhii Shymkiv
 */

public class DecryptAndMask extends SimpleTagSupport {
    private String toMask;
    private String mask;
    private int lastShown;

    public void setToMask(String toMask) {
        this.toMask = toMask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public void setLastShown(String lastShown) {
        this.lastShown =
                Integer.parseInt(lastShown);
    }

    @Override
    public void doTag()
            throws JspException, IOException {
        getJspContext().
                getOut().
                print(maskString(
                        decrypt(toMask),
                        lastShown,
                        mask));
    }
}
