
<%
    session.invalidate();
    request.getSession();
    response.sendRedirect("/");
%>