import { Component, OnInit } from '@angular/core';
import { NzModalComponent, NzModalService } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { PersonFormModalComponent } from 'src/app/components/person-form-modal/person-form-modal.component';
import { PageInfo } from 'src/app/model/page-info';
import { Person } from 'src/app/model/person';
import { PersonFilter } from 'src/app/model/person-filter';
import { PersonPageRequest } from 'src/app/model/person-page-request';
import { SortOrder } from 'src/app/model/sort-order';
import { PersonService } from 'src/app/service/person.service';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
})
export class TableComponent implements OnInit {
  persons: Person[] = [];

  loading = false;
  pageSize = 10;
  pageIndex = 1;
  total = 1;

  visibleFn = false;
  visibleLn = false;

  pageRequest: PersonPageRequest = {
    pageInfo: {
      pageSize: this.pageSize,
      pageNumber: this.pageIndex,
      sort: null,
    } as PageInfo,
    personFilter: {
      firstName: '',
      lastName: '',
    } as PersonFilter,
  };

  constructor(
    private personService: PersonService,
    private modal: NzModalService
  ) {}

  ngOnInit(): void {}

  removePerson(id: string): void {
    this.personService.delete(id).subscribe((result) => {
      this.search();
    });
  }

  onQueryParamsChange(params: NzTableQueryParams): void {
    const { pageSize, pageIndex, sort, filter } = params;
    const currentSort = sort.find(item => item.value !== null);
    const sortField = (currentSort && currentSort.key) || null;
    const sortOrder = (currentSort && currentSort.value) || null;

    this.pageRequest.pageInfo = {
      pageSize: pageSize,
      pageNumber: pageIndex,
      sort: (sortField && sortOrder) ? [{
        order: sortOrder === 'ascend' ? SortOrder.ASC : SortOrder.DESC,
        column: sortField,
      }] : null
    };
    this.search();
  }

  addPerson() {
    const modal = this.modal.create<PersonFormModalComponent, Person>({
      nzContent: PersonFormModalComponent,
      nzWidth: 900,
      nzCentered: true,
      nzData: {} as Person,
    });

    modal.afterClose.subscribe((data) => {
      console.log('[afterClose] result is: ', data);
      if (!!data) {
        this.search();
      }
    });

    modal.afterOpen.subscribe(() => {
      console.log('[afterOpen] emmited');
    });
  }

  editPerson(id: string) {
    this.personService.find(id).subscribe(result => {
      const modal = this.modal.create<PersonFormModalComponent, Person>({
        nzContent: PersonFormModalComponent,
        nzWidth: 900,
        nzCentered: true,
        nzData: result,
      });

      modal.afterClose.subscribe((data) => {
        console.log('[editPerson][afterClose] result is: ', data);
        if (!!data) {
          this.search();
        }
      });

      modal.afterOpen.subscribe(() => {
        console.log('[editPerson][afterOpen] emmited');
      });
    });
  }

  reseLastName() {
    this.pageRequest.personFilter.lastName = '';
    this.search();
  }

  resetFirstName() {
    this.pageRequest.personFilter.firstName = '';
    this.search();
  }

  search() {
    console.log('searchhhh');
    this.visibleFn = false;
    this.visibleLn = false;

    this.loading = true;
    this.personService.personPage(this.pageRequest).subscribe(
      (result) => {
        this.persons = result.rows;
        this.total = result.totalElements;
        this.loading = false;
      },
      () => (this.loading = false)
    );
  }
}
