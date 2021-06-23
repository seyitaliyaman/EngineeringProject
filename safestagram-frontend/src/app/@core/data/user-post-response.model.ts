import SmallProfileResponse from "./small-profil-response.model";

export default class UserPostResponse{
    id: number;
    postDescription: string;
    isLiked: boolean;
    createDate: Date;
    profile: SmallProfileResponse;
    postUrl: string;
    postClass: string;
}