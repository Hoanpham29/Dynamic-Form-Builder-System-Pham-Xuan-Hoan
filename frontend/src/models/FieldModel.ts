export class FieldModel {
    id: number;
    label: string;
    type: string; // text | number | date | select | color
    required: boolean;
    displayOrder: number;
    options?: string[]| undefined;

    constructor(
        id: number,
        label: string,
        type: string,
        required: boolean,
        displayOrder: number,
        options?: string[]
    ) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.required = required;
        this.displayOrder = displayOrder;
        this.options = options;
    }
}