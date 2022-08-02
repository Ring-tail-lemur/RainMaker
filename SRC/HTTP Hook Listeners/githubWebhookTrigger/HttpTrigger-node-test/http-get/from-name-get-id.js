const axios = require('axios');
async function getUserIdFromName(name){
    const uri = 'https://api.github.com/users/'+name;
    // console.log(uri);
    const headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'
    }
    const body = await axios({
        method: 'GET',
        url: uri,
        headers: headers,
    });
    // console.log(JSON.stringify(body.data.id));
    return JSON.stringify(body.data.id);
}
// getUserIdFromName("vidigummy");
module.exports.getUserId = getUserIdFromName;