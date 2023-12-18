import { PageInfo } from "./page-info";
import { PersonFilter } from "./person-filter";

export interface PersonPageRequest {
  pageInfo: PageInfo;
  personFilter: PersonFilter;
}
