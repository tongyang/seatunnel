/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.seatunnel.transform.replace;

import org.apache.seatunnel.api.configuration.util.OptionRule;
import org.apache.seatunnel.api.table.connector.TableTransform;
import org.apache.seatunnel.api.table.factory.Factory;
import org.apache.seatunnel.api.table.factory.TableTransformFactory;
import org.apache.seatunnel.api.table.factory.TableTransformFactoryContext;
import org.apache.seatunnel.transform.common.TransformCommonOptions;

import com.google.auto.service.AutoService;

@AutoService(Factory.class)
public class ReplaceTransformFactory implements TableTransformFactory {
    @Override
    public String factoryIdentifier() {
        return "Replace";
    }

    @Override
    public OptionRule optionRule() {
        return OptionRule.builder()
                .optional(
                        ReplaceTransformConfig.KEY_REPLACE_FIELD,
                        ReplaceTransformConfig.KEY_PATTERN,
                        ReplaceTransformConfig.KEY_REPLACEMENT)
                .optional(ReplaceTransformConfig.KEY_IS_REGEX)
                .conditional(
                        ReplaceTransformConfig.KEY_IS_REGEX,
                        true,
                        ReplaceTransformConfig.KEY_REPLACE_FIRST)
                .optional(TransformCommonOptions.MULTI_TABLES)
                .optional(TransformCommonOptions.TABLE_MATCH_REGEX)
                .build();
    }

    @Override
    public TableTransform createTransform(TableTransformFactoryContext context) {
        return () ->
                new ReplaceMultiCatalogTransform(context.getCatalogTables(), context.getOptions());
    }
}
