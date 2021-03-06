/*
 * Copyright 2012-2015 the original author or authors.
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
package org.ajoberstar.grgit.service

import org.ajoberstar.grgit.Branch
import org.ajoberstar.grgit.Repository
import org.ajoberstar.grgit.operation.*
import org.ajoberstar.grgit.util.JGitUtil
import org.ajoberstar.grgit.util.OpSyntaxUtil

import org.eclipse.jgit.lib.Ref

/**
 * Provides support for performing branch-related operations on
 * a Git repository.
 *
 * <p>
 *   Details of each operation's properties and methods are available on the
 *   doc page for the class. The following operations are supported directly on
 *   this service instance.
 * </p>
 *
 * <ul>
 *   <li>{@link org.ajoberstar.grgit.operation.BranchAddOp add}</li>
 *   <li>{@link org.ajoberstar.grgit.operation.BranchChangeOp change}</li>
 *   <li>{@link org.ajoberstar.grgit.operation.BranchListOp list}</li>
 *   <li>{@link org.ajoberstar.grgit.operation.BranchRemoveOp remove}</li>
 *   <li>{@link org.ajoberstar.grgit.operation.BranchStatusOp status}</li>
 * </ul>
 *
 * @since 0.2.0
 */
class BranchService {
    private static final Map OPERATIONS = [
        list: BranchListOp, add: BranchAddOp, remove: BranchRemoveOp,
        change: BranchChangeOp, status: BranchStatusOp]
    private final Repository repository

    BranchService(Repository repository) {
        this.repository = repository
    }

    /**
     * Gets the branch associated with the current HEAD.
     * @return the branch or {@code null} if the HEAD is detached
     */
    Branch getCurrent() {
        Ref ref = repository.jgit.repository.getRef('HEAD')?.target
        return ref ? JGitUtil.resolveBranch(repository, ref) : null
    }

    def methodMissing(String name, args) {
        OpSyntaxUtil.tryOp(this.class, OPERATIONS, [repository] as Object[], name, args)
    }
}
