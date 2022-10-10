async function getCurrentTime(){
    const date = new Date();
    const year = date.getUTCFullYear();
    const month = date.getUTCMonth()+1;
    const day = date.getUTCDate();
    const hour = ('0'+date.getUTCHours()).slice(-2);
    const minute = ('0'+date.getUTCMinutes()).slice(-2);
    const sec = ('0'+date.getUTCSeconds()).slice(-2);
    return `${year}-${month}-${day}T${hour}:${minute}:${sec}Z`;
}
module.exports.getCurrentTime = getCurrentTime;


