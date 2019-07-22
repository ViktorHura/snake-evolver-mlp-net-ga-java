package snakenet;

import java.util.Comparator;

public class Organism {
int inputs;
int hiddens;
int outputs;
double fitness;
Matrix inputWeights;
Matrix hiddenWeights;

public Organism(int inputs, int hiddens, int outputs){
        this.inputs = inputs;
        this.hiddens = hiddens;
        this.outputs = outputs;

        fitness = 0.0;
        inputWeights = new Matrix();
        hiddenWeights = new Matrix();
        inputWeights.RandomMatrix(hiddens, inputs);
        hiddenWeights.RandomMatrix(outputs, hiddens);
}

public int getInputs() {
        return this.inputs;
}
public void setInputs(int inputs){
        this.inputs = inputs;
}

public int getHiddens() {
        return this.hiddens;
}
public void setHiddens(int hiddens){
        this.hiddens = hiddens;
}

public int getOutputs() {
        return this.outputs;
}
public void setOutputs(int outputs){
        this.outputs = outputs;
}

public double getFitness() {
        return this.fitness;
}
public void setFitness(double fitness){
        this.fitness = fitness;
}

public Matrix getInputWeights() {
        return this.inputWeights;
}
public void setInputWeights(Matrix inputWeights){
        this.inputWeights = inputWeights;
}

public Matrix getHiddenWeights() {
        return this.hiddenWeights;
}
public void setHiddenWeights(Matrix hiddenWeights){
        this.hiddenWeights = hiddenWeights;
}

public Organism() {
        super();
}


public Organism Crossover(Organism b){

        Organism child = new Organism(this.inputs, this.hiddens, this.outputs);

        int midinput = (int)(Math.random() * ( child.inputWeights.Rows() * child.inputWeights.Cols() ));

        for (int i = 0; i< child.inputWeights.Rows(); i++) {
                for(int j=0; j< child.inputWeights.Cols(); j++) {

                        if (i * j < midinput) {
                                child.inputWeights.matrix[i][j] = this.inputWeights.matrix[i][j];
                        }else{
                                child.inputWeights.matrix[i][j] = b.inputWeights.matrix[i][j];
                        }


                }
        }

        int midhidden = (int)(Math.random() * ( child.hiddenWeights.Rows() * child.hiddenWeights.Cols() ));

        for (int i = 0; i< child.hiddenWeights.Rows(); i++) {
                for(int j=0; j< child.hiddenWeights.Cols(); j++) {

                        if (i * j < midhidden) {
                                child.hiddenWeights.matrix[i][j] = this.hiddenWeights.matrix[i][j];
                        }else{
                                child.hiddenWeights.matrix[i][j] = b.hiddenWeights.matrix[i][j];
                        }


                }
        }

        return child;
}

public void Mutate(double mutationrate){

        for (int i = 0; i< this.inputWeights.Rows(); i++) {
                for(int j=0; j< this.inputWeights.Cols(); j++) {

                        if ((Math.random() * 100.0) <  mutationrate) {
                                this.inputWeights.matrix[i][j] = Matrix.RandNorm();
                        }


                }
        }

        for (int i = 0; i< this.hiddenWeights.Rows(); i++) {
                for(int j=0; j< this.hiddenWeights.Cols(); j++) {

                        if ((Math.random() * 100.0) <  mutationrate) {
                                this.hiddenWeights.matrix[i][j] = Matrix.RandNorm();
                        }


                }
        }


}



}
