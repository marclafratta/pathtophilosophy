# Path to Philosophy

This service finds the path to philosophy from a given start address. The program returns if the philosophy article is found, the maximum number of hops is reached or if a loop is found.

## Usage

The rest enpoint listens on the /findPath/ address. One required parameter of startUrl is required. This is the starting point for the search for the philosophy article.

### Example call

findPath/?startUrl=https://en.wikipedia.org/wiki/Cicero

## Result Explanation

There are two portions of the response, pathList and foundPhilosophy. The pathList contains the list of articles traversed in order and foundPhilosophy contains true if philosophy article was found, false otherwise.