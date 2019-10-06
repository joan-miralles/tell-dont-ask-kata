package it.gabrieletondi.telldontaskkata.useCase;

public class OrderApprovalRequest {
    private int orderId;
    private boolean approved;

    public static OrderApprovalRequest approvedRequest(int orderId) {
        OrderApprovalRequest orderApprovalRequest = new OrderApprovalRequest();
        orderApprovalRequest.orderId = orderId;
        orderApprovalRequest.approved = true;
        return orderApprovalRequest;
    }

    public static OrderApprovalRequest rejectedRequest(int orderId) {
        OrderApprovalRequest rejectedRequest = new OrderApprovalRequest();
        rejectedRequest.orderId = orderId;
        rejectedRequest.approved = false;
        return rejectedRequest;
    }

    public int getOrderId() {
        return orderId;
    }

    public boolean isApproved() {
        return approved;
    }
}
