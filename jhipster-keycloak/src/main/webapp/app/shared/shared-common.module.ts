import { NgModule } from '@angular/core';

import { JhipKeycloakSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [JhipKeycloakSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [JhipKeycloakSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhipKeycloakSharedCommonModule {}
