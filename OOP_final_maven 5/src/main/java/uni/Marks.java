package uni;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Marks {
    private float firstAtt = 0;
    private float secondAtt = 0;
    private float finalExam = 0;
    private float totalGrade = 0;

    public Marks(float firstAtt, float secondAtt, float finalExam) {
        this.firstAtt = Math.min(firstAtt, 30);
        this.secondAtt = Math.min(secondAtt, 30);
        this.finalExam = Math.min(finalExam, 40);
        this.totalGrade = calculateTotalGrade();
    }

    private float calculateTotalGrade() {
        return firstAtt + secondAtt + finalExam;
    }

    public void addMark(float mark) {
        float remainingMark = mark;

        if (totalGrade < 30) {
            float availableSpace = 30 - firstAtt;
            float toAdd = Math.min(remainingMark, availableSpace);
            addMarkToFirstAtt(toAdd);
            remainingMark -= toAdd;
        }

        if (remainingMark > 0 && totalGrade < 60) {
            float availableSpace = 30 - secondAtt;
            float toAdd = Math.min(remainingMark, availableSpace);
            addMarkToSecondAtt(toAdd);
            remainingMark -= toAdd;
        }

        if (remainingMark > 0) {
            float availableSpace = 40 - finalExam;
            float toAdd = Math.min(remainingMark, availableSpace);
            addMarkToFinalExam(toAdd);
        }
    }

    public void addMarkToFirstAtt(float mark) {
        this.firstAtt = Math.min(this.firstAtt + mark, 30);
        updateTotalGrade();
    }

    public void addMarkToSecondAtt(float mark) {
        this.secondAtt = Math.min(this.secondAtt + mark, 30);
        updateTotalGrade();
    }

    public void addMarkToFinalExam(float mark) {
        this.finalExam = Math.min(this.finalExam + mark, 40);
        updateTotalGrade();
    }


    public void setFirstAtt(float firstAtt) {
        this.firstAtt = Math.min(firstAtt, 30);
        updateTotalGrade();
    }


    public void setSecondAtt(float secondAtt) {
        this.secondAtt = Math.min(secondAtt, 30);
        updateTotalGrade();
    }


    public void setFinalExam(float finalExam) {
        this.finalExam = Math.min(finalExam, 40);
        updateTotalGrade();
    }

    public float getTotalGrade() {
        return totalGrade;
    }

    private void updateTotalGrade() {
        this.totalGrade = calculateTotalGrade();
    }

    @Override
    public String toString() {
        return "Marks{" +
                "firstAtt=" + firstAtt +
                ", secondAtt=" + secondAtt +
                ", finalExam=" + finalExam +
                ", totalGrade=" + totalGrade +
                '}';
    }
}
