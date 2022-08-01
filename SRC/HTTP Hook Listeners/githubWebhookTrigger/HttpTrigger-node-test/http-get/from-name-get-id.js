const axios = require('axios');
async function getUserIdFromName(name){
    const uri = 'https://api.github.com/users/'+name;
    const headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'
    }
    const body = await axios({
        method: 'GET',
        url: uri,
        headers: headers,
    });
    return JSON.stringify(body.data.id);
}

module.exports.getUserId = getUserIdFromName;