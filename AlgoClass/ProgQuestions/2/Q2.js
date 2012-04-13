var fs = require('fs');
var sys = require('util');

fs.readFile('QuickSort.txt', 'utf8', function (err, data) {
  var i, sum, dataToUse;

  if (err) throw err;
  var dataArray = data.split('\n');

  if (dataArray.length == 10001) {
    dataArray = dataArray.slice(0,10000);
  }

  sum = 0;
  // dataArray is a table of strings, we need numbers
  for (i = 0; i < dataArray.length; i += 1) {
    dataArray[i] = parseInt(dataArray[i]);
    sum = sum + dataArray[i];
  }

  console.log("First question :");
  dataToUse = dataArray.slice(0, dataArray.length);
  console.log(quickSortAndCountComparisons(dataToUse).count);


  console.log("Second question :");
  dataToUse = dataArray.slice(0, dataArray.length);
  console.log(quickSortAndCountComparisons2(dataToUse).count);


  console.log("Third question :");
  dataToUse = dataArray.slice(0, dataArray.length);
  console.log(quickSortAndCountComparisons3(dataToUse).count);



});


function returnMedian(a, b, c) {
  if ((a - b) * (a - c) <= 0) { return 1; }
  if ((b - a) * (b - c) <= 0) { return 2; }
  if ((c - a) * (c - b) <= 0) { return 3; }
}

function swap(array, i, j) {
  var tmp = array[i];
  array[i] = array[j];
  array[j] = tmp;
}


function quickSortAndCountComparisons(arrayToSort) {
  var leftArray, rightArray
    , leftRep, rightRep
    , i, j, pivot;

  if (arrayToSort.length === 0) {
    return {sortedArray: [], count: 0};
  }

  if (arrayToSort.length === 1) {
    return {sortedArray: arrayToSort, count: 0};
  }

  pivot = arrayToSort[0];
  i = 1;

  for (j = 1; j < arrayToSort.length; j += 1) {
    if (arrayToSort[j] < pivot) {
      swap(arrayToSort, i, j);
      i += 1;
    }
  }

  swap(arrayToSort, i - 1, 0);

  leftArray = arrayToSort.slice(0, i - 1);
  rightArray = arrayToSort.slice(i, arrayToSort.length);

  leftRep = quickSortAndCountComparisons(leftArray);
  rightRep = quickSortAndCountComparisons(rightArray);


  return {sortedArray: leftRep.sortedArray.concat([pivot]).concat(rightRep.sortedArray),count: arrayToSort.length - 1 + leftRep.count + rightRep.count};
}





function quickSortAndCountComparisons2(arrayToSort) {
  var leftArray, rightArray
    , leftRep, rightRep
    , i, j, pivot;

  if (arrayToSort.length === 0) {
    return {sortedArray: [], count: 0};
  }

  if (arrayToSort.length === 1) {
    return {sortedArray: arrayToSort, count: 0};
  }

  swap(arrayToSort, 0, arrayToSort.length - 1);       // Only difference with the first one
  pivot = arrayToSort[0];
  i = 1;

  for (j = 1; j < arrayToSort.length; j += 1) {
    if (arrayToSort[j] < pivot) {
      swap(arrayToSort, i, j);
      i += 1;
    }
  }

  swap(arrayToSort, i - 1, 0);

  leftArray = arrayToSort.slice(0, i - 1);
  rightArray = arrayToSort.slice(i, arrayToSort.length);

  leftRep = quickSortAndCountComparisons2(leftArray);
  rightRep = quickSortAndCountComparisons2(rightArray);


  return {sortedArray: leftRep.sortedArray.concat([pivot]).concat(rightRep.sortedArray),count: arrayToSort.length - 1 + leftRep.count + rightRep.count};
}



function quickSortAndCountComparisons3(arrayToSort) {
  var leftArray, rightArray
    , leftRep, rightRep
    , i, j, pivot, first, middle, last, middlePosition;

  if (arrayToSort.length === 0) {
    return {sortedArray: [], count: 0};
  }

  if (arrayToSort.length === 1) {
    return {sortedArray: arrayToSort, count: 0};
  }




  if (arrayToSort.length % 2 === 0) {
    middlePosition = arrayToSort.length / 2 - 1;
  } else {
    middlePosition = (arrayToSort.length + 1) / 2 - 1;
  }


  first = arrayToSort[0];
  last = arrayToSort[arrayToSort.length - 1];
  middle = arrayToSort[middlePosition];

  if (returnMedian(first, middle, last) === 1) {

  }

  if (returnMedian(first, middle, last) === 2) {
    swap(arrayToSort, 0, middlePosition);
  }

  if (returnMedian(first, middle, last) === 3) {
    swap(arrayToSort, 0, arrayToSort.length - 1);
  }



  pivot = arrayToSort[0];
  i = 1;

  for (j = 1; j < arrayToSort.length; j += 1) {
    if (arrayToSort[j] < pivot) {
      swap(arrayToSort, i, j);
      i += 1;
    }
  }

  swap(arrayToSort, i - 1, 0);

  leftArray = arrayToSort.slice(0, i - 1);
  rightArray = arrayToSort.slice(i, arrayToSort.length);

  leftRep = quickSortAndCountComparisons3(leftArray);
  rightRep = quickSortAndCountComparisons3(rightArray);


  return {sortedArray: leftRep.sortedArray.concat([pivot]).concat(rightRep.sortedArray),count: arrayToSort.length - 1 + leftRep.count + rightRep.count};
}

