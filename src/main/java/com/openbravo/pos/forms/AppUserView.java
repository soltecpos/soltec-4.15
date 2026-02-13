/*
 * Decompiled with CFR 0.152.
 */
package com.openbravo.pos.forms;

import com.openbravo.pos.forms.AppUser;

public interface AppUserView {
    public static final String ACTION_TASKNAME = "taskname";

    public AppUser getUser();

    public void showTask(String var1);

    public void executeTask(String var1);
}

