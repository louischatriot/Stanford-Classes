var originalGraph = []
  , reversedGraph = []
  , orderSecondIteration = []
  , t = 0
  , exploredRev = []
  , exploredOrig = []
  , explorationStack = []
  , leaderCount = []
  , i
  , fs = require('fs')
  , n = 875714;    // 875714

// Empty list representation
for (i = 0; i <= n; i++) {
  originalGraph[i] = [];
  reversedGraph[i] = [];
  exploredRev[i] = false;
  exploredOrig[i] = false;
  leaderCount[i] = 0;
}

console.log("Init complete");

fs.readFile('SCC.txt', 'utf8', function (err, data) {
  var temp, currentNode, j, rootNode, tempOrder;

  if (err) throw err;
  var dataArray = data.split('\n');

  //console.log(dataArray.length);
  //console.log(dataArray[5105040]);
  //console.log(dataArray[5105041]);
  //console.log(dataArray[5105042]);


  console.log("File read");

  //5105042

  for (i = 0; i <= 5105042; i++) {
    temp = dataArray[i].split(" ");

    if (temp.length < 2) {throw {} }

    originalGraph[parseInt(temp[0])].push(parseInt(temp[1]));
    reversedGraph[parseInt(temp[1])].push(parseInt(temp[0]));
  }

  //console.log(originalGraph);
  //console.log(reversedGraph);

  console.log("Data parsed");

  tempOrder = 0;

  for (i = n; i > 0; i -= 1) {
    if (! exploredRev[i]) {
      explorationStack.push(i);
      exploredRev[i] = true;

      orderSecondIteration[tempOrder] = [];
      while (explorationStack.length > 0) {
        currentNode = explorationStack.pop();

        for (j = 0; j < reversedGraph[currentNode].length; j += 1) {
          if (! exploredRev[reversedGraph[currentNode][j]]) {
            exploredRev[reversedGraph[currentNode][j]] = true;
            explorationStack.push(reversedGraph[currentNode][j]);
          }
        }

        //tempOrder.push(currentNode);
        orderSecondIteration[tempOrder].push(currentNode);
      }

      //orderSecondIteration = tempOrder.concat(orderSecondIteration);
      tempOrder += 1;
    }
  }

  console.log("First iteration done");
  //console.log(orderSecondIteration);

  explorationStack = [];

  for (tempOrder = orderSecondIteration.length - 1; tempOrder >= 0; tempOrder -= 1) {
    for (i = 0; i < orderSecondIteration[tempOrder].length; i += 1) {

      rootNode = orderSecondIteration[tempOrder][i];

      if (! exploredOrig[rootNode]) {
        explorationStack.push(rootNode);
        exploredOrig[rootNode] = true;

        while (explorationStack.length > 0) {
          currentNode = explorationStack.pop();
          leaderCount[rootNode] += 1;

          for (j = 0; j < originalGraph[currentNode].length; j += 1) {
            if (! exploredOrig[originalGraph[currentNode][j]]) {
              exploredOrig[originalGraph[currentNode][j]] = true;
              explorationStack.push(originalGraph[currentNode][j]);
            }
          }

        }
      }

    }
  }

  console.log("Second iteration done");

  //console.log(leaderCount);

  leaderCount.sort(function (a,b) {return b - a;} );

  console.log(leaderCount[0]);
  console.log(leaderCount[1]);
  console.log(leaderCount[2]);
  console.log(leaderCount[3]);
  console.log(leaderCount[4]);


  //var bla = 0;
  //for (j = 0; j < n; j ++) { 
    //bla += orderSecondIteration[j];
    //if (orderSecondIteration[j] === 0) { console.log("==== " + j);}
  //}


  //console.log(bla);
  //console.log(n * (n+1) / 2);
});



var firstIteration = function(root) {
  var j;

  exploredRev[root] = true;

  for (j = 0; j < reversedGraph[root].length; j += 1) {
    if (! exploredRev[reversedGraph[root][j]]) {
      firstIteration(j);
    }
  }

  t += 1;
  finishingtimes[root] = t;
}





