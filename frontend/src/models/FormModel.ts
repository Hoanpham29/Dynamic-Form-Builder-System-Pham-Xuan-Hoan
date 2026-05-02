import { FieldModel } from "./FieldModel";

export class FormModel {
    id: number;
    title: string;
    description: string;
    status: string;
    displayOrder: number;
    fields: FieldModel[];

    constructor(id: number, title: string, description: string, status: string, displayOrder: number, fields: FieldModel[] = []) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.displayOrder = displayOrder;
        this.fields = fields;
    }
}