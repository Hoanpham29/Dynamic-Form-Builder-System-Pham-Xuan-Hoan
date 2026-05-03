import { AnswerModel } from "./AnswerModel";

export class SubmissionModel{
    id: number;
    formTitle: string;
    submittedAt: string;
    answers: AnswerModel[];
    
    constructor(
    id: number,
    formTitle: string,
    submittedAt: string,
    answers: AnswerModel[]
  ) {
    this.id = id;
    this.formTitle = formTitle;
    this.submittedAt = submittedAt;
    this.answers = answers;
  }
}