import React from "react";
import "../styles/Administration.css";

const Administration = () => {
    var customObjectList = JSON.parse('${usersToConfirm}');
    console.log(customObjectList);
  return <div className="container">

      {/*<form:form method="POST" modelAttribute="userForConfirmation">*/}
          {/*<c:forEach items="${userForConfirmation}" var="user">*/}
              {/*<tr>*/}
                  {/*<td>this.state.user.id</td>*/}
                  {/*/!*<td>${user.username}</td>*!/*/}
                  {/*/!*<td>${user.email}</td>*!/*/}
                  {/*<td><a href="/administration/user-confirm/${user.id}/accept">accept</a></td>*/}
                  {/*<td><a href="/administration/user-confirm/${user.id}/reject">reject</a></td>*/}
              {/*</tr>*/}
          {/*</c:forEach>*/}
      {/*</form:form>*/}

  </div>;
};

export default Administration;
