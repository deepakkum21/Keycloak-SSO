import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { JhipKeycloakSharedLibsModule, JhipKeycloakSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [JhipKeycloakSharedLibsModule, JhipKeycloakSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    exports: [JhipKeycloakSharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipKeycloakSharedModule {
    static forRoot() {
        return {
            ngModule: JhipKeycloakSharedModule
        };
    }
}
