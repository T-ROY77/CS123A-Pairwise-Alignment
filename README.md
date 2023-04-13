Pairwise Alignment Tool

This is a pairwise alignment tool used to generate an optimal alignment for nucleotide and protein sequences. 
The tool supports 2 different modes:
Nucleotide mode will calculate an optimal alignment using nucleotide match/mismatch scoring. 
For a nucleotide match, the score is +1. For a nucleotide mismatch, the score is -1.

Protein mode will calculate an optimal alignment based on a scoring matrix loaded into the tool.

To start the tool, run main and follow the prompts printed in the console.

The tool will first print the sequences currently loaded. If no sequences are loaded, they will prompt the user to enter sequences
The tool will prompt user with multiple options:

1. Enter new query sequences
-The tool will ask for a name for the first sequence. Pressing enter with nothing typed will set the name to "Sequence 1:"
-The tool will ask for the first sequence. The sequence may only include alphabet letters excluding o, u, and j, as there are not amino acids associated with them 
-The tool will ask for a name for the second sequence. Pressing enter with nothing typed will set the name to "Sequence 2:"
-The tool will ask for the second sequence
-If both sequences are valid, they will show in the sequences field.

2. Enter new query sequences with filepath
-The tool supports filepath input for query sequences
-filepath must be absolute
-Files must be in FASTA format
-filepath input supports multiple line sequences
-error with entering sequences will print error and sequences will not be loaded

3. Calculate an optimal alignment
-The main function of the tool is to calculate and print an optimal alignment for the query sequences
-If no sequences are loaded, the alignment will not be calculated
-If tool is in protein mode, the tool will prompt the user to select a matrix to compare amino acids
  -If no valid matrix is selected, the BLOSUM-62 matrix will be used
-If tool is in nucleotide mode, alignment will be calculated with match/mismatch scoring of +1 and -1 respectively


4. Print grid
-The tool will print the Needleman Wunsch grid calculated 
-If no sequences are loaded, the grid will not be printed
-If sequences have been loaded, but the alignment has not been calculated, the grid will not be printed
-In the grid, "/" represents an empty grid
-Sequence one is loaded on the horizontal axis
-Sequence two is loaded on the vertical axis

5. Print arrow grid
-The tool will print the Needleman Wunsch grid arrow directions 
-If no sequences are loaded, the grid will not be printed
-If sequences have been loaded, but the alignment has not been calculated, the grid will not be printed
-In the grid, "/" represents an empty grid
-In the grid, "^" represents a north arrow
-In the grid, "<-" represents a west arrow
-In the grid, "^<-" represents a north west arrow
-Sequence one is loaded on the horizontal axis
-Sequence two is loaded on the vertical axis

6. Change mode (current mode)
-The tool will switch modes(Nucleotide mode or Protein mode)
-This will empty the sequence fields and reset the grid
-Nucleotide mode will calculate an optimal alignment using match/mismatch score of +1 and -1 respectively
-Protein mode will use a user selected matrix to calculate match/mismatch score

7. Create a new matrix
-The tool supports adding new matrices during runtime
-The input is based on the ncbi standard matrix format: https://www.ncbi.nlm.nih.gov/IEB/ToolBox/C_DOC/lxr/source/data/BLOSUM62 
-The tool will prompt user for matrix name
-The tool will then prompt user for amino acid order
 -The amino acid input can be one string of lowercase or uppercase letters
 -The amino acid input should not include duplicates, or the letters o, u, or j
-The tool will then prompt user for matrix scores
 -the matrix scores should be inputted as positive or negative integers space seperated
 -The matrix scores should be inputted left to right, top to bottom
 -The matrix scores should include the entire matrix, not the half full matrix
 -The number of matrix scores should be the square of the number of amino acids
-If all inputs are valid, the matrix will be created and added to the grid object
-If any inputs are invalid, the matrix will not be created
-This new matrix can be selected when calculating an optimal alignment in protein mode

8. Print all matrices
-prints all matrices loaded into the grid
-prints new matrices created during runtime

9.quit
-exits the program




