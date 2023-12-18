import { Component, Input, OnInit, inject } from '@angular/core';
import { Person } from 'src/app/model/person';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PersonService } from 'src/app/service/person.service';
import { NzModalRef, NzModalService, NZ_MODAL_DATA } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-person-form-modal',
  templateUrl: './person-form-modal.component.html',
  styleUrls: ['./person-form-modal.component.css'],
})
export class PersonFormModalComponent implements OnInit {
  // @Input() person!: Person;

  personForm!: FormGroup;
  person: Person = inject(NZ_MODAL_DATA);

  constructor(
    private personService: PersonService,
    private fb: FormBuilder,
    private modalRef: NzModalRef
  ) {}

  ngOnInit(): void {
    console.log('nzModalData: ', this.person);
    this.personForm = this.fb.group({
      id: [null],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      age: [null, [Validators.required]],
      address: [null, [Validators.required]],
    });

    if (this.person && this.person.id) {
      this.personForm.setValue(this.person);
    }
  }

  closeModal(): void {
    this.modalRef.destroy();
  }

  submitForm(): void {
    for (const i in this.personForm.controls) {
      if (this.personForm.controls.hasOwnProperty(i)) {
        this.personForm.controls[i].markAsDirty();
        this.personForm.controls[i].updateValueAndValidity();
      }
    }

    if (this.personForm.valid) {
      if (this.person.id) {
        this.personService.save(this.personForm.value).subscribe((result) => {
          this.modalRef.destroy(result);
        });
      } else {
        this.personService.create(this.personForm.value).subscribe((result) => {
          this.modalRef.destroy(result);
        });
      }
    }
  }
}
