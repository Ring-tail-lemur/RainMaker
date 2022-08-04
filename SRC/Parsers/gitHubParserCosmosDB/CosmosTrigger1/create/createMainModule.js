
async function createMain(eventObject, context) {
    //ref의 type은 branch||tag임, 현재 보고 있는 것은 branch, ref_type이 branch인 것만 처리, tag는 do nothing.
}

module.exports.createMain = createMain;