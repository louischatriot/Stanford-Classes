var fs = require('fs')
  , dataArray = []
  , theData = []
  , asObject = {}
  , i
  , n = 100000;


fs.readFile('HashInt.txt', 'utf8', function (err, data) {
  if (err) throw err;

  dataArray = data.split('\n');

  for (i = 0; i < n; i += 1) {
    theData[i] = parseInt(dataArray[i]);
    asObject[theData[i]] = true;
  }

  console.log(twoSumPossible(231552));
  console.log(twoSumPossible(234756));
  console.log(twoSumPossible(596873));
  console.log(twoSumPossible(648219));
  console.log(twoSumPossible(726312));
  console.log(twoSumPossible(981237));
  console.log(twoSumPossible(988331));
  console.log(twoSumPossible(1277361));
  console.log(twoSumPossible(1283379));
});


var twoSumPossible = function(sum) {
  for (i = 0; i < n; i += 1) {
    if (asObject[sum - theData[i]]) {
      return true;
    }
  }

  return false;
}
