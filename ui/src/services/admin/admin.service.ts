/**
 * @license
 * Copyright 2020 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {BaseService} from "../baseService";
import {Rule} from "../../models";

/**
 * The Admin service.  Used to get global/settings information from the back-end, like global
 * rules and logging settings.
 */
export class AdminService extends BaseService {

    public getRules(): Promise<Rule[]> {
        this.logger.info("[AdminService] Getting the global list of rules.");
        const endpoint: string = this.endpoint("/v2/admin/rules");
        return this.httpGet<string[]>(endpoint).then( ruleTypes => {
            return Promise.all(ruleTypes.map(rt => this.getRule(rt)));
        });
    }

    public getRule(type: string): Promise<Rule> {
        const endpoint: string = this.endpoint("/v2/admin/rules/:rule", {
            rule: type
        });
        return this.httpGet<Rule>(endpoint);
    }

    public createRule(type: string, config: string): Promise<Rule> {
        this.logger.info("[AdminService] Creating global rule:", type);

        const endpoint: string = this.endpoint("/v2/admin/rules");
        const body: Rule = {
            config,
            type
        };
        return this.httpPostWithReturn(endpoint, body);
    }

    public updateRule(type: string, config: string): Promise<Rule|null> {
        this.logger.info("[AdminService] Updating global rule:", type);

        const endpoint: string = this.endpoint("/v2/admin/rules/:rule", {
            "rule": type
        });
        const body: Rule = { config, type };
        return this.httpPutWithReturn<Rule, Rule>(endpoint, body);
    }

    public deleteRule(type: string): Promise<null> {
        this.logger.info("[AdminService] Deleting global rule:", type);

        const endpoint: string = this.endpoint("/v2/admin/rules/:rule", {
            "rule": type
        });
        return this.httpDelete(endpoint);
    }

}
