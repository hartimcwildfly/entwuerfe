package de.dhcd.entwuerfe.model.draft;


import java.time.OffsetDateTime;

import de.dhcd.entwuerfe.model.Approver;
import de.dhcd.entwuerfe.model.EntwurfStatus;


public class ApprovedDraft extends Draft {
    
    private final Approver       approver;
    private final String         approvedComment;
    private final OffsetDateTime approvedAt;
    
    ApprovedDraft(PendingDraft pendingDraft, Approver approver, String approvedComment, OffsetDateTime approvedAt) {
        super(
                pendingDraft.getUuid(),
                pendingDraft.getContent(),
                pendingDraft.getKundennummer(),
                pendingDraft.getKundenname(),
                pendingDraft.getProjektnummer(),
                pendingDraft.getProjektname(),
                pendingDraft.getCreatedAt());
        this.approver = approver;
        this.approvedComment = approvedComment;
        this.approvedAt = approvedAt;
    }
    
    @Override
    public EntwurfStatus getStatus() {
        return EntwurfStatus.APPROVED;
    }
}
