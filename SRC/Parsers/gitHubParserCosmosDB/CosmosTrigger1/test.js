const index = require('./index.js');

// const jsonData = require('./_test_json_file/pullRequsetReviewTest.json');


 const jsonData1 = require('./_test_json_file/repositoryTest.json');
 const jsonData2 = require('./_test_json_file/branchTest.json');
 const jsonData3 = require('./_test_json_file/branchTest2.json');
 const jsonData4 = require('./_test_json_file/pullRequsetOpenTest.json');
 const jsonData5 = require('./_test_json_file/pullRequsetReviewTest.json');
 const jsonData6 = require('./_test_json_file/commitTest.json');
const jsonData7 = require('./_test_json_file/pullRequsetClosedTest.json');

const a = []
// a.push(jsonData1);
// a.push(jsonData2);
// a.push(jsonData3);
// a.push(jsonData4);
// a.push(jsonData5);
// a.push(jsonData6);
a.push(jsonData7);
const context = console
index(context,a);