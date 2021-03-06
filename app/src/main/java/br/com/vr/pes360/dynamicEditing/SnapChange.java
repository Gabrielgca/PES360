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

package br.com.vr.pes360.dynamicEditing;

public class SnapChange {
    private int milliseconds;
    private int roiDegrees;
    private int[] foVTiles;
    private boolean millisecondsFlag;
    private boolean roiDegreesFlag;
    private boolean foVTilesFlag;

    public SnapChange() {
        this.millisecondsFlag = this.roiDegreesFlag = this.foVTilesFlag = false;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
        this.millisecondsFlag = true;
    }

    public void setRoiDegrees(int roiDegrees) {
        this.roiDegrees = roiDegrees;
        this.roiDegreesFlag = true;
    }

    public void setFoVTiles(int[] foVTiles) {
        this.foVTiles = foVTiles;
        this.foVTilesFlag = true;
    }

    public boolean isOK() {
        return this.millisecondsFlag && this.roiDegreesFlag && this.foVTilesFlag;
    }

    public int getSCMilliseconds() {
        return this.milliseconds;
    }

    public long getSCMicroseconds() {
        return this.milliseconds * 1000;
    }

    public int getSCroiDegrees() {
        return this.roiDegrees;
    }

    public int[] getSCfoVTiles() {
        return this.foVTiles;
    }
}
