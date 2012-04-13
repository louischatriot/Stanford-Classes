var fs = require('fs');
var sys = require('util');


fs.readFile('Q1.txt', 'utf8', function (err, data) {
  var i;

  if (err) throw err;
  var dataArray = data.split('\n');

  if (dataArray.length == 100001) {
    dataArray = dataArray.slice(0,100000);
  }

  // dataArray is a table of strings, we need numbers
  for (i = 0; i < dataArray.length; i += 1) {
    dataArray[i] = parseInt(dataArray[i]);
  }

  console.log(naiveMethod(dataArray));
  console.log(mergeAndCount(dataArray).inversions);
});

var naiveMethod = function(arr) {
  var res = 0,
      i,
      j,
      l = arr.length;
  
  for (i = 0;i < l; i += 1) {
    for (j = i + 1; j < l; j++) {
      res = res + (arr[i] > arr[j] ? 1 : 0);
    }
  }

  return res;
};

var showRetMAC = function(o) {
  console.log("=== " + o.arr.toString() + " - " + o.inversions);
}

var mergeAndCount = function (arr) {
  var l = arr.length,
      a1 = arr.slice(0, Math.floor(l/2)),
      a2 = arr.slice(Math.floor(l/2), l),
      a1_mac, a2_mac,
      mergedArr = [],
      inversionsBetweenA1A2 = 0,
      i,
      inversionsMicroCounter = 0;

  //console.log("== Call with arr " + arr.toString());
  //console.log("a1 " + a1.toString());
  //console.log("a2 " + a2.toString());
  
  if (l === 0) {
    return {arr: [], inversions: 0};
  }

  if (l === 1) {
    return {arr: arr, inversions: 0};
  }



  a1_mac = mergeAndCount(a1);
  a2_mac = mergeAndCount(a2);

  //console.log("a1_mac " + a1_mac.arr.toString());
  //console.log("a2_mac " + a2_mac.arr.toString());


  for (i = 0; i < l; i += 1) {
    if (a1_mac.arr.length === 0) {
      mergedArr.push(a2_mac.arr.shift());
    } else if (a2_mac.arr.length === 0) {
      inversionsBetweenA1A2 += inversionsMicroCounter;
      mergedArr.push(a1_mac.arr.shift());
    } else if (a1_mac.arr[0] > a2_mac.arr[0]) {
      inversionsMicroCounter += 1;
      mergedArr.push(a2_mac.arr.shift());
    } else {
      inversionsBetweenA1A2 += inversionsMicroCounter;
      mergedArr.push(a1_mac.arr.shift());
    }
  }

  //console.log("== merged array : ", mergedArr.toString());

  return {arr: mergedArr, inversions: inversionsBetweenA1A2 + a1_mac.inversions + a2_mac.inversions};
}





