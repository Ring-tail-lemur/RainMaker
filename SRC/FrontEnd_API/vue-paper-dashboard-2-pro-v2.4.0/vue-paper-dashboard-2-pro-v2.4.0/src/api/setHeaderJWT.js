function setHeaderJWT() {
    const jwtToken = getCookie("SESSIONID");
    
    return {
        SessionID: jwtToken,
    };
}

function getCookie(name) {
    let nameOfCookie = name + "=";
    let x = 0;
    while (x <= document.cookie.length) {
        let y = (x + nameOfCookie.length);
        if (document.cookie.substring(x, y) == nameOfCookie) {
            let endOfCookie;
            if ((endOfCookie = document.cookie.indexOf(";", y)) == -1)
                endOfCookie = document.cookie.length;
            return unescape(document.cookie.substring(y, endOfCookie));
        }
        x = document.cookie.indexOf(" ", x) + 1;
        if (x == 0)
            break;
    }
   return "";
}

export default setHeaderJWT;
