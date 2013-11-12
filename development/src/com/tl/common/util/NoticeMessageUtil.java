package com.tl.common.util;

import java.util.Collection;
import java.util.Iterator;

import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class NoticeMessageUtil {

  public void sendMessage(String pscript, String pageUrl) {
    WebContext wctx = WebContextFactory.get();
    ServerContext sctx = ServerContextFactory.get(wctx.getServletContext());
    Collection pages = sctx.getScriptSessionsByPage(pageUrl);
    ScriptBuffer script = new ScriptBuffer();
    script.appendScript(pscript);
    for (Iterator iterator = pages.iterator(); iterator.hasNext();) {
      ScriptSession session = (ScriptSession) iterator.next();
      session.addScript(script);
    }
  }

}
