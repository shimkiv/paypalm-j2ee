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

import static com.shimkiv.paypalm.utils.CommonUtils.generateUniqueRef;

/**
 * Custom JSP tag (convert ID to UniqueRef string)
 *
 * @author Serhii Shymkiv
 */

public class IdToUniqueRef extends SimpleTagSupport {
    private int id;

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    @Override
    public void doTag()
            throws JspException, IOException {
        getJspContext().
                getOut().
                print(generateUniqueRef(id));
    }
}
