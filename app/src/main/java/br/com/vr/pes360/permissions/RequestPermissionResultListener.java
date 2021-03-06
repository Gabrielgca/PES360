/*
 * Copyright 2017 Université Nice Sophia Antipolis (member of Université Côte d'Azur), CNRS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.vr.pes360.permissions;

/**
 * Listener holding the callback method called when requesting permission using the
 * PermissionManager singleton.
 */
public interface RequestPermissionResultListener {

    /**
     * @param requestID The id of the request made to the manager.
     * @param result    either PERMISSION_GRANTED or PERMISSION_DENIED from PackageManager.
     */
    void onPermissionRequestDone(int requestID, int result);
}
